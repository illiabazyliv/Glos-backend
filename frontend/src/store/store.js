import { applyMiddleware, combineReducers, createStore, compose } from "redux";
import { thunk } from 'redux-thunk';
import authReducer from "./reducers/authReducer";
import appReducer from "./reducers/appReducer";
import repositoryReducer from "./reducers/repositoryReducer";
import fileReducer from "./reducers/fileReducer";

const reducers = combineReducers({
    appReducer,
    authReducer,
    repositoryReducer,
    fileReducer,
});

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(reducers, composeEnhancers(
    applyMiddleware(thunk)
));

export default store;