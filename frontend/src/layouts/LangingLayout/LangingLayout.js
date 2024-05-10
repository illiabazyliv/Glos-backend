import { Outlet } from 'react-router-dom';
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

function LandingLayout() {
    return (
        <>
            <Header />
            <Outlet/>
            <Footer />
        </>
        
    );
  }
  
  export default LandingLayout;