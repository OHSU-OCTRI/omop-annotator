import { createApp } from 'vue';
import MeasurementList from './components/MeasurementList';

const dataset = document.querySelector('#measurement_list').dataset;
const app = createApp(MeasurementList, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null,
  visitId: dataset.visitId ? Number.parseInt(dataset.visitId) : null,
  showHeader: dataset.showHeader === 'false' ? false : true
});
app.mount('#measurement_list');
