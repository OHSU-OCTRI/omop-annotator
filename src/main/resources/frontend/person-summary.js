import { createApp } from 'vue';
import PersonSummary from './components/PersonSummary';

const dataset = document.querySelector("#person_summary").dataset;
const app = createApp(PersonSummary, { 
    contextPath: dataset.contextPath,
    personId: dataset.personId ? Number.parseInt(dataset.personId) : null
});
app.mount('#person_summary');