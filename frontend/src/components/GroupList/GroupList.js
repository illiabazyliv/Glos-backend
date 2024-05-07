import GroupItem from "../GroupItem/GroupItem";
import Pagination from "../Pagination/Pagination";

function GroupList({ groups, errors = [], onPageChange }) {
    return (
        <div className="group-list">
            <div className="row">
                {
                    errors.map(err => <div className="col-12 text-danger" key={err} >
                        <p>{err}</p>
                    </div>)
                }
                {
                    groups && groups.length ? groups.map(item => <div className="col-12" key={item.groupName} >
                        <GroupItem group={item} />
                    </div>) : <p className="text-center">No groups found.</p>
                }
            </div>
        </div>
    );
}

export default GroupList;