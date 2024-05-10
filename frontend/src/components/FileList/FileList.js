import FileItem from "../FileItem/FileItem";
import Pagination from "../Pagination/Pagination";

function FileList({ files, errors, onPageChange }) {
    return (
        <div className="file-list">
            <div className="row">
                {
                    errors.map(err => <div className="col-12 text-danger" key={err} >
                        <p>{err}</p>
                    </div>)
                }
                {
                    files?.content ? files.content.map(item => <div className="col-12" key={item.displayFullName} >
                        <FileItem file={item} />
                    </div>) : <p className="text-center">No results found.</p>
                }
            </div>
            {
                files?.content?.length ? 
                <Pagination currentPage={files?.page || 1} 
                        totalPages={files?.totalSize ? Math.ceil(files.totalSize / files.size) : 1}
                        onPageChange={onPageChange}/> : null
            }
        </div>
    );
}

export default FileList;