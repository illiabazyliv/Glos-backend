import { Outlet } from 'react-router-dom';
import DashboardHeader from '../../components/DashboardHeader/DashboardHeader';
import { useState } from 'react';
import NewRepositoryModal from '../../components/NewRepositoryModal/NewRepositoryModal';
import NewFileModal from '../../components/NewFileModal/NewFileModal';

function DashboardLayout() {
    const [isSidebarVisible, setSidebarVisible] = useState(window.innerWidth >= 992 ? true : false);

    return (
        <div className={isSidebarVisible ? 'toggle-sidebar' : ''}>
            <DashboardHeader isSidebarVisible={isSidebarVisible} setSidebarVisible={setSidebarVisible}/>
            <Outlet/>
            <NewFileModal/>
            <NewRepositoryModal/>
        </div>
        
    );
  }
  
export default DashboardLayout;