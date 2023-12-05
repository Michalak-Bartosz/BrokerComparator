import React from "react";
import { IoGitCompareSharp } from "react-icons/io5";
import { Link, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import useApi from "../connections/api/useApi";
import { signal } from "@preact/signals-react";

export const username = signal("");

function Navbar() {
  const accessToken = useSelector((state) => state.token.accessToken);
  const navigate = useNavigate();
  const api = useApi();

  return (
    <div
      id="navbar"
      className="nav fixed flex p-4 w-full h-32 bg-slate-800 text-white top-0 left-0"
    >
      <div id="title-wrapper" className="flex my-auto">
        <IoGitCompareSharp className="text-6xl text-blue-500 mr-4 my-auto" />
        <h1 className="font-bold text-4xl my-auto">
          Broker Comparator Dashboard
        </h1>
      </div>
      <div id="button-wrapper" className="flex my-auto ml-auto">
        {accessToken !== undefined ? (
          <div className="flex">
            <div className="flex">
              <h1 className="m-auto font-bold text-2xl">User:&nbsp;</h1>
              <h1 className="m-auto font-bold text-2xl text-green-400">
                {username}
              </h1>
            </div>
            <Link
              id="logout-link"
              className="m-4 px-4 py-2 bg-slate-200 text-slate-800 text-2xl font-bold rounded-md outline outline-offset-2 outline-blue-500 hover:bg-blue-300"
              to="/"
              onClick={() => {
                api.logOutUser();
                navigate("/");
              }}
            >
              Log Out
            </Link>
          </div>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export default Navbar;
