import { Outlet } from 'react-router-dom';

function DashboardLayout() {
    return (
        <>
            <div>dashboard header here</div>
            <Outlet/>
            <div>dashboard footer here</div>
        </>
        
    );
  }
  
  export default DashboardLayout;