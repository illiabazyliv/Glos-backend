import { applyMiddleware, combineReducers, createStore, compose } from "redux";
import { thunk } from 'redux-thunk';
import authReducer from "./reducers/authReducer";
import appReducer from "./reducers/appReducer";

const reducers = combineReducers({
    appReducer,
    authReducer,
});

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(reducers, composeEnhancers(
    applyMiddleware(thunk)
));

export default store;