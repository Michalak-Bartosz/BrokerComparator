import React from "react";
import { signal } from "@preact/signals-react";

export const authErrorMessage = signal(null);
export function ErrorMessage() {
  return (
    <div id="error-message" className="flex m-auto mb-12">
      {authErrorMessage.value && (
        <h1 className="text-center transition-all text-white text-2xl w-96 p-2 mt-6 font-bold items-center rounded-md m-auto bg-rose-700">
          {authErrorMessage.value}
        </h1>
      )}
    </div>
  );
}

export default ErrorMessage;
