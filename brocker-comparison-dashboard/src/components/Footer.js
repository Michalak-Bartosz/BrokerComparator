import React from "react";
import { Footer } from "flowbite-react";
import { BsGithub } from "react-icons/bs";

function DasboardFooter() {
  return (
    <Footer
      container
      className="absolute h-16 bottom-0 bg-slate-800 rounded-none"
    >
      <Footer.Copyright by="Bartosz Michalak" year={2023} />
      <Footer.LinkGroup>
        <Footer.Icon
          href="https://github.com/Michalak-Bartosz"
          target="_blank"
          icon={BsGithub}
        />
      </Footer.LinkGroup>
    </Footer>
  );
  // <div id="footer" className="relative absolute flex min-w-full mt-auto mb-0 bottom-0 h-24 bg-slate-800">
  //           <p className="text-center m-auto text-bold text-2xl text-white">
  //         Copyright 2023 Bartosz Michalak. All Rights Reserved
  //       </p>
  // </div>;
}

export default DasboardFooter;
