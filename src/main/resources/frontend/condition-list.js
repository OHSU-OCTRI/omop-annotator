import { createApp } from 'vue';
import ConditionList from './components/ConditionList';

const dataset = document.querySelector('#condition_list').dataset;
const app = createApp(ConditionList, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null
});
app.mount('#condition_list');
