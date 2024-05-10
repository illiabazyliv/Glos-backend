import { NavLink } from "react-router-dom";
import FileDropdown from "../FileDropdown/FileDropdown";
import GroupDropdown from "../GroupDropdown/GroupDropdown";
import { createRef } from "react";

function GroupItem({ group }) {
    // todo: choode image placeholder based on type of file
    const targetModalBtnRef = createRef();
    return (
        <div className="card card-list-item group-item d-flex flex-row mb-2">
            <div className="card-img">
                <img src="https://i0.wp.com/sunrisedaycamp.org/wp-content/uploads/2020/10/placeholder.png?ssl=1" className="img-fluid rounded-start"/>
            </div>
            <div className="card-body p-2 d-flex justify-content-between gap-2">
                <h6 className="fs-6 text-medium card-title d-flex align-items-center p-0 m-0">
                    <a className="dropdown-item d-flex align-items-center" href="#" 
                        onClick={(e) => {e.preventDefault(); targetModalBtnRef.current.click();}}>
                        {group.groupName}
                    </a>
                </h6>
                <GroupDropdown groupId={group.groupName} btnRef={targetModalBtnRef}/>
            </div>
        </div>
    );
}

export default GroupItem;