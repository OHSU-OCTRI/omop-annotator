<template>
  <div><svg></svg></div>
</template>

<script>
function parseDateString(dateString) {
  return new Date(Date.parse(dateString));
}
import * as d3 from 'd3';
import _ from 'lodash';
// import { countBy } from 'lodash/array';

export default {
  props: {
    width: {
      type: Number,
      default: 200
    },
    height: {
      type: Number,
      default: 20
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
    const data = d3.range(this.dataCount).map(d => Math.random());
    const x = d3.scaleLinear().domain([0, this.dataCount]).range([0, this.width]);
    const y = d3.scaleLinear().domain([0, 1]).range([this.height, 0]);

    const svg = d3
      .select('svg')
      .attr('width', this.width)
      .attr('height', this.height)
      .append('g');
    svg
      .selectAll('.bar')
      .data(data)
      .enter()
      .append('rect')
      .attr('class', 'bar')
      .attr('x', (d, i) => x(i))
      .attr('y', d => this.height - y(d))
      .attr('width', this.barWidth)
      .attr('height', d => y(d))
      .attr('fill', 'steelblue');
  },
  computed: {
    visitDates() {
      // NOTE: that this excludes visits without a date.
      return this.visits.map(visit => visit.visitStart).filter(dt => dt);
    },
    visitDateCounts() {
      // Group by date, ignoring time.
      let counts = _.countBy(this.visitDates, stamp => {
        let datetime = parseDateString(stamp);
        datetime.setUTCHours(0, 0, 0, 0);
        return datetime.toISOString();
        // return datetime;
      });
      return Object.entries(counts).map(entry => {
        return { date: parseDateString(entry[0]), count: entry[1] };
      });
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
