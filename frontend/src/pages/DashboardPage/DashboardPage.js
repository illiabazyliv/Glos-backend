import { NavLink, Outlet } from 'react-router-dom';
import DashboardSidebar from '../../components/DashboardSidebar/DashboardSidebar';

function DashboardPage() {
    return (
        <div>
            <DashboardSidebar/>
            <main className='main'>
                <div className="inner-page">
                    <Outlet/>
                </div>
            </main>
        </div>
    );
  }
  
  export default DashboardPage;