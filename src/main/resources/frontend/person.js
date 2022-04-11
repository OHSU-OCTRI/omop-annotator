import { createApp } from 'vue';

import PersonDataBrowser from './components/PersonDataBrowser';

const dataset = document.querySelector('#person_browser').dataset;
const app = createApp(PersonDataBrowser, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null
});
app.mount('#person_browser');
