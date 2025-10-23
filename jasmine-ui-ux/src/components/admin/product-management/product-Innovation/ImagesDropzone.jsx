import React from "react";
import { storage } from "../../../config/firebase";
import { useDropzone } from "react-dropzone";

export default function ImagesDropzone({ setImageList }) {
  const onDrop = (acceptedFiles) => {
    if (acceptedFiles.length > 0) {
      console.log("acceptedFiles", acceptedFiles);
      const newImages = Array.from(acceptedFiles).map((file) => {
        return {
          file: file,
          storageRef: storage.ref().child(file.name),
          status: "UPLOADING",
          downloadURL: "",
        };
      });

      setImageList((prevState) => [...prevState, ...newImages]);
    }
  };

  const { getRootProps, getInputProps, open } = useDropzone({
    onDrop,
    accept: "image/png, image/jpeg",
    noClick: true,
    noKeyboard: true,
  });

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      <div className="add-image" onClick={open}>
        <i className="fa-solid fa-plus"></i>
      </div>
    </div>
  );
}
