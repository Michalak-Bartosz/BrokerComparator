import { Modal } from "flowbite-react";
import { FaWindowClose } from "react-icons/fa";
import { FaDotCircle } from "react-icons/fa";
import React from "react";

function AddressModal({ openModal, setOpenModal, address }) {
  return (
    <div id="address-modal-wrapper">
      <Modal show={openModal} size="3xl" onClose={() => setOpenModal(false)}>
        <Modal.Header>Address Info</Modal.Header>
        <Modal.Body>
          <div className="text-xl m-auto">
            <div className="flex items-center">
              <FaDotCircle className="text-blue-500 text-sm mr-1" />
              <span className="mr-1 font-bold">UUID:</span>
              <span>{address.uuid}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-blue-500 text-sm mr-1" />
              <span className="mr-1 font-bold">User UUID:</span>
              <span>{address.userUuid}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-blue-500 text-sm mr-1" />
              <span className="mr-1 font-bold">Street Name:</span>
              <span>{address.streetName}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-blue-500 text-sm mr-1" />
              <span className="mr-1 font-bold">Number:</span>
              <span>{address.number}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-blue-500 text-sm mr-1" />
              <span className="mr-1 font-bold">City:</span>
              <span>{address.city}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-blue-500 text-sm mr-1" />
              <span className="mr-1 font-bold">Country:</span>
              <span>{address.country}</span>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer className="p-0">
          <button
            id="close-test-data-modal-button"
            className="flex items-center mx-auto my-6 px-2 py-1 outline outline-offset-2 outline-rose-700 rounded-md bg-rose-800 hover:bg-rose-700 disabled:bg-slate-400 disabled:outline-slate-500 text-white font-bold text-xl"
            onClick={() => setOpenModal(false)}
          >
            <FaWindowClose className="text-white mr-4" />
            Close
          </button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default AddressModal;
