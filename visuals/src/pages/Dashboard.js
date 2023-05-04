import React, {useState} from "react";
import Papa from 'papaparse';
import api from '../api/axiosConfig';


import "./dashboard.scss";

const Dashboard = () => {

    const [columnArray, setColumn] = useState([]);
    const [values, setValues] = useState([]);

    const getUsers = async () => {
        await api.get("/users")
            .then(function (response) {
                const valuesArray = [];
                response.data.map(d => valuesArray.push([d.firstName,d.lastName,d.email, d.phone]))
                setValues(valuesArray);
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    const getDuplicateUsers = async () => {
        await api.get("/duplicate-users")
            .then(function (response) {
                const valuesArray = [];
                response.data.map(d => valuesArray.push([d.firstName,d.lastName,d.email, d.phone]))
                setValues(valuesArray);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    const handleFileUpload = async (e) => {
    setColumn(['First Name','Last Name','Email', 'Phone Number']);
    Papa.parse(e.target.files[0], {
    header: true,
    skipEmptyLines: true,
    complete: function(result) {

        result.data.map((d) => {
            api.post('/users', {
            firstName: d.FirstName,
            lastName: d.LastName,
            email: d.Email,
            phone: d.Phone
        })
            .catch(function (error) {
                console.log(error);
            });
        })
      }
    })
        getDuplicateUsers();
}

    return(
        <div className="dashboard">
            <label className="dashboard__form">
                Upload CSV file
                <input
                    type="file"
                    name="file"
                    accept=".csv"
                    id="file-upload"
                    onChange={handleFileUpload}
                ></input>
            </label> <br/>
            <label className="dashboard__form">
                Show Duplicates
                <input
                    type="button"
                    name="file"
                    id="file-upload"
                    hidden={true}
                    onClick={getDuplicateUsers}
                ></input>
            </label>

            <br/>
            {columnArray ?
                <table className="dashboard__table">
                    <thead>
                    <tr>
                        {columnArray.map((cols, i) => (
                            <th key={i}>{cols}</th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {values.map((v, i) => (
                        <tr key={i}>
                            {v.map((value, i) => (
                                <td key={i}>{value}</td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table> : " "}
        </div>
    );
};

export default Dashboard;