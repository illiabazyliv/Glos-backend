import { Outlet } from 'react-router-dom';
import DashboardHeader from '../../components/DashboardHeader/DashboardHeader';
import { useState } from 'react';

function DashboardLayout() {
    const [isSidebarVisible, setSidebarVisible] = useState(window.innerWidth >= 992 ? true : false);

    return (
        <div className={isSidebarVisible ? 'toggle-sidebar' : ''}>
            <DashboardHeader isSidebarVisible={isSidebarVisible} setSidebarVisible={setSidebarVisible}/>
            <Outlet/>
        </div>
        
    );
  }
  
export default DashboardLayout;