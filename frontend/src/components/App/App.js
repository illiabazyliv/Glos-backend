import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import '../../../node_modules/bootstrap/dist/js/bootstrap.min.js';
import '../../assets/theme/style.css';
import '../../assets/css/style.css';
import Header from '../Header/Header';
import HomePage from '../../pages/HomePage/HomePage';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from '../Footer/Footer.js';

function App() {
  return (
      <BrowserRouter>
          <Header/>
          <Routes>
            <Route path='/' element={<HomePage />} />
          </Routes>
          <Footer/>
      </BrowserRouter>
  );
}

export default App;
