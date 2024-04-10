import React, {useEffect} from 'react';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import '../../../node_modules/bootstrap/dist/js/bootstrap.min.js';
import '../../../node_modules/bootstrap-icons/font/bootstrap-icons.css';
import '../../assets/css/theme.css';
import '../../assets/css/style.css';
import store from '../../store/store';
import { Provider } from 'react-redux';
import { initialize } from '../../store/thunks/appThunks';
import { connect } from 'react-redux';
import Loader from '../Loader/Loader';
import HomePage from '../../pages/HomePage/HomePage';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import DashboardPage from '../../pages/DashboardPage/DashboardPage.js';
import FilesPage from '../../pages/FilesPage/FilesPage.js';
import LandingLayout from '../../layouts/LangingLayout/LangingLayout.js';
import DashboardLayout from '../../layouts/DashboardLayout/DashboardLayout.js';


const App = ({ isInitialized, initialize, user }) => {
    useEffect(() => {
        if (!isInitialized) initialize();
    }, []);

    console.log(user)

    if (!isInitialized) {
        return <Loader />
    }

    return (
        <BrowserRouter>
            <Routes>
                <Route element={<LandingLayout/>}>
                    <Route path='/' element={<HomePage />} />
                </Route>

                <Route element={<DashboardLayout/>}>
                    <Route path='/dashboard/*' element={<DashboardPage />}>
                        <Route index element={<Navigate to={'/dashboard/all-files'}/>}/>
                        <Route path='all-files' element={<FilesPage />} />
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

const mapStateToProps = (state) => {
    return {
        isInitialized: state.appReducer.isInitialized,
        user: state.authReducer.user,
    }
}

const mapDispatchToProps = {
    initialize
};

const AppContainer = connect(mapStateToProps, mapDispatchToProps)(App);

const FinalApp = () => {
    return (
        <div className="app">
            <Provider store={store}>
                <AppContainer />
            </Provider>
        </div>
    );
}

export default FinalApp;
