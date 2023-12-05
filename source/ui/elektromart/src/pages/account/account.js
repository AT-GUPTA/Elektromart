// account.js

import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Swal from 'sweetalert2';

function Account({logout}) {
  const [currentPasscode, setCurrentPasscode] = useState('');
  const [newPasscode, setNewPasscode] = useState('');
  const [newPasscodeConfirmation, setNewPasscodeConfirmation] = useState('');

  const handleChangePasscode = async () => {
    try {
      if (!currentPasscode) {
        throw new Error('Current Passcode is required.');
      }
  
      if (newPasscode.length < 4 || !/^[a-zA-Z0-9]+$/.test(newPasscode)) {
        throw new Error('Passcode must be at least 4 alphanumeric characters.');
      }
  
      if (newPasscode !== newPasscodeConfirmation) {
        throw new Error('New Passcode and Confirm New Passcode must match.');
      }

      const token = localStorage.getItem("secret");
      const response = await fetch('http://localhost:3000/api/auth/passcode', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          oldPasscode: currentPasscode,
          newPasscode,
          newPasscodeConfirmation,
        }),
      });
  
      if (response.ok) {
      
        setCurrentPasscode('');
        setNewPasscode('');
        setNewPasscodeConfirmation('');

        Swal.fire({
          icon: 'success',
          title: 'Success!',
          text: 'Password changed successfully. Please login again with your new passcode.',
          timer: 3000,
          willClose: () => {
            localStorage.removeItem("isAuth");
            localStorage.removeItem("roleId");
            localStorage.removeItem("cart_id");
            localStorage.removeItem("id");
            localStorage.removeItem("secret");
            localStorage.clear();
            sessionStorage.clear();
            window.location.href = "/login";
          }
        });
       // logout();
      } else {
        const data = await response.json();
        throw new Error(data.message || 'Failed to change passcode.');
      }
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'An error occurred. The new passcode is probably taken already, or the old passcode provided is incorrect.',
      });
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Change Passcode</h2>
      <div className="card">
        <div className="card-body">
          <form>
            <div className="mb-3">
              <label htmlFor="currentPasscode" className="form-label">
                Current Passcode
              </label>
              <input
                className="form-control"
                id="currentPasscode"
                value={currentPasscode}
                required
                onChange={(e) => setCurrentPasscode(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="newPasscode" className="form-label">
                New Passcode
              </label>
              <input
                className="form-control"
                id="newPasscode"
                value={newPasscode}
                onChange={(e) => setNewPasscode(e.target.value)}
              />
              {newPasscode.length > 0 && newPasscode.length < 4 && (
                <div className="text-danger">Passcode must be at least 4 characters.</div>
              )}
            </div>
            <div className="mb-3">
              <label htmlFor="confirmNewPasscode" className="form-label">
                Confirm New Passcode
              </label>
              <input
                className="form-control"
                id="confirmNewPasscode"
                value={newPasscodeConfirmation}
                onChange={(e) => setNewPasscodeConfirmation(e.target.value)}
              />
            </div>
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleChangePasscode}
            >
              Change Passcode
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Account;
