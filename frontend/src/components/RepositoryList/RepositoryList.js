import RepositoryItem from "../RepositoryItem/RepositoryItem";

function RepositoryList({ repositories }) {
    return (
        <div className="file-list">
            <div className="row">
                {
                    repositories.map(item => <div className="col-12" key={item.displayname} >
                        <RepositoryItem repository={item} />
                    </div>)
                }
            </div>
        </div>
    );
}

export default RepositoryList;