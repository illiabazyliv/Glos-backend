import { NavLink } from 'react-router-dom';
import { ReactComponent as Logo } from "../../assets/svg/logo.svg";
import HeaderProfileMenu from '../HeaderProfileMenu/HeaderProfileMenu'
import { connect } from 'react-redux';

function DashboardHeader({ user, userImage, isSidebarVisible, setSidebarVisible }) {
	return (
		<header className='header fixed-top d-flex align-items-center'>
			<nav className="navbar navbar-expand-lg navbar-light py-4 w-100">
				<div className="container-fluid d-flex justify-content-between">
					<div className="d-flex justify-content-between">
						<NavLink to="/" className="d-flex align-items-center">
							<Logo className="logo" />
						</NavLink>
						<button className="navbar-toggler ms-4" type="button"
							onClick={() => { setSidebarVisible(!isSidebarVisible) }}>
							<span className="navbar-toggler-icon"></span>
						</button>
					</div>
					<HeaderProfileMenu user={user} userImage={userImage}/>
				</div>
			</nav>
		</header>
	);
}

const mapStateToProps = (state) => {
	return {
		user: state.authReducer.user,
		userImage: state.authReducer.userImage,
	}
}

const mapDispatchToProps = {

};

export default connect(mapStateToProps, mapDispatchToProps)((DashboardHeader));