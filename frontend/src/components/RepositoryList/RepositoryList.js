import RepositoryItem from "../RepositoryItem/RepositoryItem";
import Pagination from "../Pagination/Pagination";

function RepositoryList({ repositories, errors, onPageChange }) {
    return (
        <div className="file-list">
            <div className="row">
                {
                    errors.map(err => <div className="col-12 text-danger" key={err} >
                        <p>{err}</p>
                    </div>)
                }
                {
                    repositories?.content?.length ? repositories.content.map(item => <div className="col-12" key={item.displayname} >
                        <RepositoryItem repository={item} />
                    </div>) : <p className="text-center">No results found.</p>
                }
            </div>
            {
                repositories?.content?.length ? 
                <Pagination currentPage={repositories?.page || 1} 
                totalPages={repositories?.totalSize ? Math.ceil(repositories.totalSize / repositories.size) : 1}
                onPageChange={onPageChange}/> : null
            }
        </div>
    );
}

export default RepositoryList;