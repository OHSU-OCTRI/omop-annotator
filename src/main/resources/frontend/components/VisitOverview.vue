<template>
  <div><svg class="timeline"></svg></div>
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

    // determine circle radius based on the count value.
    const circleScale = d3
      .scaleLinear()
      .domain([0, d3.max(data.map(entry => entry.count))])
      .range([2, 6]);

    // x.ticks(10); // TODO: something with this
    const translateY = 10;
    // const cy = 2;

    // Initialize SVG

    const svg = d3
      .select('svg.timeline')
      .attr('width', this.width + 100)
      .attr('height', this.height);
    const container = svg.append('g').attr('transform', `translate(40, ${translateY})`); // TODO: property for margins

    // Define the div for the tooltip
    const tooltipDiv = d3
      .select('body')
      .append('div')
      .attr('class', 'tooltip')
      .style('opacity', 0);

    container
      .selectAll('circle')
      .data(data)
      .join('circle')
      .attr('class', 'timeline-circle')
      .attr('r', d => circleScale(d.count))
      .attr('cy', 8)
      .attr('cx', d => x(d.date))
      .on('mouseover', (event, d) => {
        tooltipDiv.transition().duration(200).style('opacity', 0.9);
        tooltipDiv
          .html(this.tooltip(d))
          .style('left', event.pageX + 'px')
          .style('top', event.pageY - 32 + 'px');
      })
      .on('mouseout', (e, d) => {
        tooltipDiv.transition().duration(500).style('opacity', 0);
      })
      .on('click', (e, d) => {
        alert(d.date.toDateString());
      });

    container
      .append('text')
      .attr('class', 'timeline-label')
      .attr('x', x(this.firstItem.date))
      .text(format(this.firstItem.date, 'MM-yyyy'));
    // .attr('transform', 'translate(10, 30)');

    container
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
      });
      let dateCounts = Object.entries(counts).map(entry => {
        return { date: parseISO(entry[0]), count: entry[1] };
      });
      dateCounts.sort((a, b) => a.date - b.date);
      return dateCounts;
    },
    firstItem() {
      return this.visitDateCounts[0];
    },
    lastItem() {
      return this.visitDateCounts[this.visitDateCounts.length - 1];
    },
    dataCount() {
      return this.visits.length;
    }
  },
  methods: {
    tooltip(item) {
      let visit = 'visit';
      if (item.count && item.count > 1) {
        visit += 's';
      }
      return `${item.count} ${visit}<br>${item.date.toDateString()}`;
    }
  }
};
</script>
