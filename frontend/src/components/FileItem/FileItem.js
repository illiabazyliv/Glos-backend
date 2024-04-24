import { NavLink } from "react-router-dom";
import FileDropdown from "../FileDropdown/FileDropdown";

function FileItem({ file }) {
    // todo: choode image placeholder based on type of file
    return (
        <div className="card card-list-item file-item d-flex flex-row mb-2">
            <div className="card-img">
                <img src="https://i0.wp.com/sunrisedaycamp.org/wp-content/uploads/2020/10/placeholder.png?ssl=1" className="img-fluid rounded-start"/>
            </div>
            <div className="card-body p-2 d-flex justify-content-between gap-2">
                <h6 className="fs-6 text-medium card-title p-0 m-0">
                    <NavLink to={'files/' + file.displayFilename}>{file.displayFilename}</NavLink>
                </h6>
                <FileDropdown fileId={file.displayFilename}/>
            </div>
        </div>
    );
}

export default FileItem;