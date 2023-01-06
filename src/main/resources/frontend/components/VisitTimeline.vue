<template>
  <div>
    <svg class="timeline" :width="timelineWidth" :height="height">
      <g class="timeline-container" transform="translate(40, 10)">
        <circle
          v-for="visitDateCount in visitDateCounts"
          :key="visitDateCount.date"
          class="timeline-circle"
          :class="{ 'timeline-circle-selected': isSelectedDate(visitDateCount.date) }"
          :r="circleScale(visitDateCount.count)"
          :cy="circleY"
          :cx="xScale(visitDateCount.date)"
          @click="selectDate(visitDateCount)"
          @mouseenter="event => showTooltip(visitDateCount, event)"
          @mouseleave="hideTooltip"
        ></circle>
        <text
          class="timeline-label"
          text-anchor="middle"
          :x="xScale(this.firstItem.date)"
          :y="labelY"
        >
          {{ firstItemLabel }}
        </text>
        <text
          class="timeline-label"
          text-anchor="middle"
          :x="xScale(this.lastItem.date)"
          :y="labelY"
        >
          {{ lastItemLabel }}
        </text>
      </g>
    </svg>

    <Transition name="tooltip">
      <div class="timeline-tooltip" :style="tooltipStyle" v-if="shouldDisplayTooltip">
        {{ tooltipPrefix }}<br />{{ tooltipItem.date.toDateString() }}
      </div>
    </Transition>
  </div>
</template>

<script>
import * as d3 from 'd3';
import { countBy } from 'lodash';
import { format, parseISO } from 'date-fns';

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
    },
    circleY: {
      type: Number,
      default: 8
    },
    labelY: {
      type: Number,
      default: 30
    },
    tooltipHeight: {
      type: Number,
      default: 40
    }
  },

  data() {
    return { tooltipItem: null, tooltipCoords: { left: 0, top: 0 }, selectedDate: null };
  },
  mounted() {},
  computed: {
    timelineWidth() {
      return this.width + 100;
    },

    visitDates() {
      // NOTE: that this excludes visits without a date.
      return this.visits.map(visit => visit.visitStart).filter(dt => dt);
    },
    visitDateCounts() {
      // Group by date, ignoring time.
      let counts = countBy(this.visitDates, stamp => {
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
    },
    xScale() {
      return d3
        .scaleTime()
        .domain(d3.extent(this.visitDateCounts.map(entry => entry.date)))
        .range([0, this.width]);
    },
    circleScale() {
      return d3
        .scaleLinear()
        .domain([0, d3.max(this.visitDateCounts.map(entry => entry.count))])
        .range([2, 6]);
    },
    firstItemLabel() {
      return format(this.firstItem.date, 'MM-yyyy');
    },
    lastItemLabel() {
      return format(this.lastItem.date, 'MM-yyyy');
    },
    tooltipPrefix() {
      if (this.tooltipItem) {
        const count = this.tooltipItem.count;
        let visit = 'visit';
        if (count && count > 1) {
          visit += 's';
        }
        return `${count} ${visit}`;
      }
      return '';
    },
    tooltipStyle() {
      return { height: `${this.tooltipHeight}px`, ...this.tooltipCoords };
    },
    shouldDisplayTooltip() {
      return this.tooltipItem !== null;
    }
  },
  methods: {
    tooltip(item) {
      let visit = 'visit';
      if (item.count && item.count > 1) {
        visit += 's';
      }
      return `${item.count} ${visit}<br>${item.date.toDateString()}`;
    },
    showTooltip(visitDateCount, event) {
      this.tooltipItem = visitDateCount;
      this.tooltipCoords = {
        top: `${event.pageY - this.tooltipHeight}px`,
        left: `${event.pageX}px`
      };
    },
    selectDate(visitDate) {
      const dt = visitDate.date;
      if (dt === this.selectedDate) {
        this.selectedDate = null;
        this.$emit('date-selected', null);
      } else {
        this.selectedDate = dt;
        this.$emit('date-selected', dt);
      }
    },
    hideTooltip() {
      this.tooltipItem = null;
    },
    isSelectedDate(date) {
      return this.selectedDate && this.selectedDate === date;
    }
  }
};
</script>
