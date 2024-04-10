import { NavLink, Outlet } from 'react-router-dom';

function DashboardPage() {
    return (
        <main>
            <div className="container-fluid">
                <div className="row">
                    <div className="col-3">
                        <h1>DashboardPage</h1>
                        <p>menu will be here</p>
                    </div>
                    <div className='col-9'>
                        <Outlet/>
                    </div>
                </div>
            </div>
        </main>
        
    );
  }
  
  export default DashboardPage;