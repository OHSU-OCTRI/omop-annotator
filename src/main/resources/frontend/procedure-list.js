import { createApp } from 'vue';
import ProcedureList from './components/ProcedureList';

const dataset = document.querySelector('#procedure_list').dataset;
const app = createApp(ProcedureList, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null,
  visitId: dataset.visitId ? Number.parseInt(dataset.visitId) : null,
  showHeader: dataset.showHeader === 'false' ? false : true
});
app.mount('#procedure_list');
