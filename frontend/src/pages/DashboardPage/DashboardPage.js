import { NavLink, Outlet } from 'react-router-dom';
import DashboardSidebar from '../../components/DashboardSidebar/DashboardSidebar';

function DashboardPage() {
    return (
        <div className='dashboard-page'>
            <DashboardSidebar/>
            <main id='main' className='main w-100'>
                <Outlet/>
            </main>
        </div>
    );
  }
  
  export default DashboardPage;