import { NavLink } from "react-router-dom";
import ItemMenuDropdown from "../ItemMenuDropdown/ItemMenuDropdown";

function RepositoryItem({ repository }) {
    // todo: choode image placeholder based on type of file
    return (
        <div class="card card-list-item repository-item d-flex flex-row mb-2">
            <div className="card-img">
                <img src="https://i0.wp.com/sunrisedaycamp.org/wp-content/uploads/2020/10/placeholder.png?ssl=1" class="img-fluid rounded-start"/>
            </div>
            <div class="card-body p-2 d-flex justify-content-between align-items-center gap-2">
                <div>
                    <h6 className="fs-6 text-medium card-title p-0 m-0">
                        <NavLink to={repository.displayname}>{repository.displayname}</NavLink>
                    </h6>
                    <small className="text-muted py-1">{repository.description}</small>
                </div>
                <ItemMenuDropdown/>
            </div>
        </div>
    );
}

export default RepositoryItem;