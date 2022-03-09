import $ from 'jquery';
import Vue from 'vue';
import Counter from './components/Counter';

new Vue({
	el: '#counter',
	  
	render(createElement) {
		return createElement(Counter);
	}
});

