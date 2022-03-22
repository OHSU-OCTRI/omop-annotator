import { createApp } from 'vue';
import VisitList from './components/VisitList';

const dataset = document.querySelector("#judgment").dataset;
const app = createApp(VisitList, { 
    contextPath: dataset.contextPath,
    personId: Number.parseInt(dataset.personId)
});
app.mount('#judgment');