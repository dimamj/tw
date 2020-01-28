import './sass/main.sass';
import ReactDom from 'react-dom';
import Test from './test';

ReactDom.render(<Test text='Whew'/>, document.getElementById('app-container'));