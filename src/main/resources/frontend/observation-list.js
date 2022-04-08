import { createApp } from 'vue';
import ObservationList from './components/ObservationList';

const dataset = document.querySelector('#observation_list').dataset;
const app = createApp(ObservationList, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null,
  visitId: dataset.visitId ? Number.parseInt(dataset.visitId) : null,
  showHeader: dataset.showHeader === 'false' ? false : true
});
app.mount('#observation_list');
