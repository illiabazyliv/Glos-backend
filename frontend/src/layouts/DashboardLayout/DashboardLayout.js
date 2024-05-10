import { Outlet } from 'react-router-dom';
import DashboardHeader from '../../components/DashboardHeader/DashboardHeader';
import { useState } from 'react';
import NewRepositoryModal from '../../components/NewRepositoryModal/NewRepositoryModal';
import NewFileModal from '../../components/NewFileModal/NewFileModal';
import EditFileModal from '../../components/EditFileModal/EditFileModal';
import EditRepositoryModal from '../../components/EditRepositoryModal/EditRepositoryModal';
import ShareRepositoryModal from '../../components/ShareRepositoryModal/ShareRepositoryModal';
import ShareFileModal from '../../components/ShareFileModal/ShareFileModal';
import DeleteFileModal from '../../components/DeleteFileModal/DeleteFileModal';
import DeleteRepositoryModal from '../../components/DeleteRepositoryModal/DeleteRepositoryModal';
import SearchModal from '../../components/SearchModal/SearchModal';
import NewFriendModal from '../../components/NewFriendModal/NewFriendModal';
import NewGroupModal from '../../components/NewGroupModal/NewGroupModal';
import EditGroupModal from '../../components/EditGroupModal/EditGroupModal';
import DeleteGroupModal from '../../components/DeleteGroupModal/DeleteGroupModal';
import GroupUsersModal from '../../components/GroupUsersModal/GroupUsersModal';

function DashboardLayout() {
    const [isSidebarVisible, setSidebarVisible] = useState(window.innerWidth >= 992 ? true : false);

    return (
        <div className={isSidebarVisible ? 'toggle-sidebar' : ''}>
            <DashboardHeader isSidebarVisible={isSidebarVisible} setSidebarVisible={setSidebarVisible}/>
            <Outlet/>
            <SearchModal/>
            <NewFileModal/>
            <NewRepositoryModal/>
            <EditFileModal/>
            <EditRepositoryModal/>
            <ShareFileModal/>
            <ShareRepositoryModal/>
            <DeleteFileModal/>
            <DeleteRepositoryModal/>
            <NewFriendModal/>
            <NewGroupModal/>
            <EditGroupModal/>
            <DeleteGroupModal/>
            <GroupUsersModal/>
        </div>
        
    );
  }
  
export default DashboardLayout;