import { createApp } from 'vue';
import VisitList from './components/VisitList';

const dataset = document.querySelector('#visit_list').dataset;
const app = createApp(VisitList, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null,
  showHeader: dataset.showHeader === 'false' ? false : true
});
app.mount('#visit_list');
