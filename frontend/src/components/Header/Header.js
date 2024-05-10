import { connect } from 'react-redux';
import { NavLink } from 'react-router-dom';
import { ReactComponent as Logo } from "../../assets/svg/logo.svg";

function Header({isAuth, isLoading}) {
	return (
		<header className='site-header accent-bg'>
			<nav className="navbar navbar-expand-lg navbar-light py-4">
				<div className="container d-flex justify-content-between">
					<NavLink to="/" className="d-flex col-8 col-lg-2 align-items-center">
						<Logo className="logo" />
					</NavLink>

					<button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggler">
						<span className="navbar-toggler-icon"></span>
					</button>

					<div className="collapse navbar-collapse justify-content-center align-items-center col-12 col-lg-8" id="navbarToggler">
						<ul className="navbar-nav pt-3 pt-lg-0">
							<li className="nav-item mx-lg-2">
								<NavLink className="nav-link" to="/">Головна</NavLink>
							</li>
							<li className="nav-item mx-lg-2">
								<NavLink className="nav-link" to="/features">Наші переваги</NavLink>
							</li>
							<li className="nav-item mx-lg-2">
								<NavLink className="nav-link" to="/contacts">Контакти</NavLink>
							</li>
							{
								isLoading ? null : isAuth ?
									<li className="nav-item mx-lg-2">
										<NavLink className="nav-link" to="/dashboard">Панель користувача</NavLink>
									</li> : 
									<li className="nav-item mx-lg-2">
										<NavLink className="nav-link" to="/login">Вхід</NavLink>
									</li>
							}
							<li className="nav-item d-block d-lg-none">
								<NavLink className="btn btn-primary" to="/register">Розпочати</NavLink>
							</li>
						</ul>
					</div>

					<div className="col-2 d-none d-lg-flex justify-content-end align-items-center">
						<NavLink className="btn btn-primary" to="/register">Розпочати</NavLink>
					</div>
				</div>
			</nav>
		</header>

	);
}

const mapStateToProps = (state) => {
    return {
        isAuth: state.authReducer.isAuth,
        isLoading: state.authReducer.isLoading,
    }
}

const mapDispatchToProps = {
    
};

export default connect(mapStateToProps, mapDispatchToProps)((Header));