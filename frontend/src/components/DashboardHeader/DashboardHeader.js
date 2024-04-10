import { NavLink } from 'react-router-dom';
import { ReactComponent as Logo } from "../../assets/svg/logo.svg";

function DashboardHeader({isSidebarVisible, setSidebarVisible}) {
  return (
	<header className='header fixed-top d-flex align-items-center'>
		<nav className="navbar navbar-expand-lg navbar-light py-4">
			<div className="container d-flex justify-content-between">
                <NavLink to="/" className="d-flex align-items-center">
                    <Logo className="logo"/>
                </NavLink>

				<button className="navbar-toggler ms-4" type="button"
                        onClick={() => {setSidebarVisible(!isSidebarVisible)}}>
					<span className="navbar-toggler-icon"></span>
				</button>
			</div>
		</nav>
	</header>
		
  );
}

export default DashboardHeader;