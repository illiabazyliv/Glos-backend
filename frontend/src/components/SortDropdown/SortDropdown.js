import { NavLink } from 'react-router-dom';

function SortDropdown({ selectedOption, sortByOptions, onChange }) {
    return (
        <div className='header-profile'>
            <a className="" href="#" data-bs-toggle="dropdown">
                Sort
            </a>

            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile p-3">
                <li>
                    <div className="form-group">
                        <label>Sort by:</label>
                        <select className="form-control" value={selectedOption.value} onChange={(e) => {onChange(e.target.value)}}>
                            {
                                sortByOptions.map(item => {
                                    return <option value={item.value} key={item.value}>{item.name}</option>
                                })
                            }
                        </select>
                    </div>
                </li>
            </ul>
        </div>
    );
}

export default SortDropdown;