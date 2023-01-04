<template>
  <div><svg></svg></div>
</template>

<script>
import * as d3 from 'd3';
import _ from 'lodash';
import { format, parseISO } from 'date-fns';
// import { countBy } from 'lodash/array';

export default {
  props: {
    width: {
      type: Number,
      default: 700
    },
    height: {
      type: Number,
      default: 50
    },
    maxBins: {
      type: Number,
      default: 40
    },
    visits: {
      type: Array,
      required: true
    }
  },

  data() {
    return {};
  },
  mounted() {
    const data = this.visitDateCounts;
    const x = d3
      .scaleTime()
      .domain(d3.extent(data.map(entry => entry.date)))
      .range([0, this.width]);
    const y = d3
      .scaleLinear()
      .domain([0, d3.max(data.map(entry => entry.count))])
      .range([this.height, 0]);

    // determine circle radius based on the count value.
    const circleScale = d3
      .scaleLinear()
      .domain([0, d3.max(data.map(entry => entry.count))])
      .range([2, 6]);

    x.ticks(10); // TODO: something with this

    const svg = d3
      .select('svg')
      .attr('width', this.width + 100)
      .attr('height', this.height)
      .append('g')
      .attr('transform', 'translate(10, 10)'); // TODO: property for margins

    svg
      .selectAll('circle')
      .data(data)
      .join('circle')
      .attr('class', 'timeline-circle')
      .attr('r', d => circleScale(d.count))
      .attr('cy', 8)
      .attr('cx', d => x(d.date));

    svg
      .append('text')
      .attr('class', 'timeline-label')
      .attr('x', x(this.firstItem.date))
      .text(format(this.firstItem.date, 'MM-yyyy'));
    // .attr('transform', 'translate(10, 30)');

    svg
      .append('text')
      .attr('class', 'timeline-label')
      .attr('x', x(this.lastItem.date))
      .text(format(this.lastItem.date, 'MM-yyyy'));
  },
  computed: {
    visitDates() {
      // NOTE: that this excludes visits without a date.
      return this.visits.map(visit => visit.visitStart).filter(dt => dt);
    },
    visitDateCounts() {
      // Group by date, ignoring time.
      let counts = _.countBy(this.visitDates, stamp => {
        let datetime = parseISO(stamp);
        datetime.setUTCHours(0, 0, 0, 0);
        return datetime.toISOString();
        // return datetime;
      });
      let dateCounts = Object.entries(counts).map(entry => {
        return { date: parseISO(entry[0]), count: entry[1] };
      });
      dateCounts.sort((a, b) => a.date - b.date);
      return dateCounts;
    },
    firstItem() {
      // TODO: use month / year
      return this.visitDateCounts[0];
    },
    lastItem() {
      // TODO: month/year
      return this.visitDateCounts[this.visitDateCounts.length - 1];
    },
    dataCount() {
      return Math.min(this.visits.length, this.maxBins);
    },
    barWidth() {
      return (this.width - this.dataCount) / this.dataCount;
    }
  },
  methods: {}
};
</script>
