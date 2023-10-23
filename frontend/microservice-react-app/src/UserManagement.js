
import React, { useState, useEffect } from 'react';
import axios from 'axios';
const publicIp = require("react-public-ip");

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [name, setName] = useState('');
    const [selectedUserId, setSelectedUserId] = useState(null);
    const [ip1, setIP] = useState('');
    const updateIP = async () => {
        publicIp.v4() .then( async ip => {
            try {
                const response = await axios.get(`/api/users/ip/${ip}`);
                setUsers(response.data);
            } catch (error) {
                console.error("Error fetching users:", error);
            }
            // ip is the value, do your logic here
            console.log(ip)
         }).catch(error => {
            // if it throws an error, you can catch it and suppress it here
            throw error;
        })

    };




    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            const response = await axios.get('/api/users/all');
            publicIp.v4() .then( async ip => {
                try {

                    const response = await axios.post(`/api/users/ip/`,{ ip});

                } catch (error) {
                    console.error("Error fetching users:", error);
                }
                // ip is the value, do your logic here
                console.log(ip)
            }).catch(error => {
                // if it throws an error, you can catch it and suppress it here
                throw error;
            })


            setUsers(response.data);
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    };

    const createUser = async () => {
        try {
            await axios.post('/api/users', { name });
            setName('');
            fetchUsers();
        } catch (error) {
            console.error("Error creating user:", error);
        }
    };

    const updateUser = async () => {
        try {
            await axios.put(`/api/users/${selectedUserId}`, { name });
            setName('');
            setSelectedUserId(null);
            fetchUsers();
        } catch (error) {
            console.error("Error updating user:", error);
        }
    };

    const deleteUser = async userId => {
        try {
            await axios.delete(`/api/users/${userId}`);
            fetchUsers();
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    };

    return (
        <div>
            <h1>User Management</h1>
            <div>
                <input
                    value={name}
                    onChange={e => setName(e.target.value)}
                    placeholder="Enter name"
                />
                {selectedUserId ? (
                    <button onClick={updateUser}>Update</button>
                ) : (
                    <button onClick={createUser}>Create</button>
                )}
            </div>
            <ul>
                {users.map(user => (
                    <li key={user.id}>
                        {user.name}
                        <button onClick={() => setSelectedUserId(user.id)}>Edit</button>
                        <button onClick={() => deleteUser(user.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UserManagement;