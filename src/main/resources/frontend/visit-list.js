import { createApp } from 'vue';
import VisitList from './components/VisitList';

const dataset = document.querySelector('#visit-list').dataset;
const app = createApp(VisitList, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null
});
app.mount('#visit-list');
