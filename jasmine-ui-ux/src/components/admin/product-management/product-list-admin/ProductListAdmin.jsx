import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import "./product-list-admin.css";
import { ProductService } from "../../../misc/ProductService";
import Pagination from '@mui/material/Pagination';
import { Link } from "react-router-dom";

const ProductListAdmin = () => {
  const [products, setProducts] = useState([]);

  //attributes for searching
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [activeFlg, setactiveFlg] = useState(5);

  const [searchingParams, setSearchingParams] = useState({});
  const [oldParams, setOldParams] = useState({});

  //selection list
  const [categories, setCategories] = useState([]);
  const [productTypes, setProductTypes] = useState([]);
  const options = [
    { key: "0", text: "--Vui lòng chọn--", value: null },
    { key: "1", text: "Bán Chạy Nhất", value: "SALE-DESC" },
    { key: "2", text: "Tồn Kho Nhiều", value: "SALE-ASC" },
  ];

  //get product categories and product types from server
  useEffect(() => {
    showCategories();
    showProductTypes();
  }, []);

  const handleInputChange = (e) => {
    var { name, value } = e.target;
    if ((name === "categoryId" || name === "productTypeId") && value == "0") {
      value = null;
    }
    setSearchingParams((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  const handleactiveFlgChange = (value) => {
    setactiveFlg(value);
    setPage(1);
  };

  const handleSearchClick = (e) => {
    e.preventDefault();
    showProducts(true);
  };

  const getSearchingParams = (page, pageSize, activeFlg, obj) => {
    let params = obj;

    if (page) {
      params["page"] = page - 1;
    }
    if (pageSize) {
      params["pageSize"] = pageSize;
    }
    params["activeFlg"] = activeFlg;
    return params;
  };

  const showProducts = (search) => {
    let params = {};
    if (search === true) {
      //client click on button search
      params = getSearchingParams(page, pageSize, activeFlg, searchingParams); //get the new values of searching params to search
      setOldParams(searchingParams); //save the current searching params
    } else {
      //the client just change the activeFlg or page or pageSize, not search
      params = getSearchingParams(page, pageSize, activeFlg, oldParams); //get the old values of searching params that saved lasted to search
    }

    ProductService.getAllProductsAdmin(params)
      .then((response) => {
        setProducts(response.data.content);
        setTotalPages(response.data.totalPages);
        setTotalElements(response.data.totalElements);
      })
      .catch((err) => console.error);
  };

  useEffect(showProducts, [page, pageSize, activeFlg]);

  const deleteItem = (id) => {
    ProductService.deleteProduct(id)
      .then(() => {
        showProducts();
      })
      .catch((error) => {
        console.error("Error deleting product: ", error);
      });
  };

  const showCategories = () => {
    ProductService.getAllCategories()
      .then((res) => {
        setCategories(res.data);
      })
      .catch((err1) => console.error);
  };

  const showProductTypes = () => {
    ProductService.getAllProductTypes()
      .then((res) => {
        setProductTypes(res.data);
      })
      .catch((err2) => console.error);
  };

  return (
    <div className="container-fluid">
      <div className="container mt-5 mb-3 product-table">
        <div>
          <form className="searching-form row" onSubmit={handleSearchClick}>
            <div className="col-lg-2 col-6 mb-2 row">
              <label>
                Mã Sản Phẩm
                <input
                  type="text"
                  name="productCode"
                  value={searchingParams.productCode}
                  onChange={handleInputChange}
                  className="form-control "
                  placeholder="Vui lòng nhập..."
                />
              </label>
            </div>

            <div className="col-lg-3 col-6 mb-2 row">
              <label>
                Tên Sản Phẩm
                <input
                  type="text"
                  name="productName"
                  value={searchingParams.productName}
                  onChange={handleInputChange}
                  className="form-control "
                  placeholder="Vui lòng nhập..."
                />
              </label>
            </div>

            <div className="col-lg-2 col-6 row">
              <label>
                Danh Mục
                <select
                  name="categoryId"
                  id="categoryId"
                  value={searchingParams.categoryId}
                  onChange={handleInputChange}
                  className="form-control "
                >
                  <option value={0}>--Vui lòng chọn--</option>
                  {categories.map((item) => (
                    <option key={item.categoryId} value={item.categoryId}>
                      {item.categoryName}
                    </option>
                  ))}
                </select>
              </label>
            </div>

            <div className="col-lg-2 col-6 row">
              <label>
                Dòng sản phẩm
                <select
                  name="productTypeId"
                  id="productTypeId"
                  value={searchingParams.productTypeId}
                  onChange={handleInputChange}
                  className="form-control "
                >
                  <option value={0}>--Vui lòng chọn--</option>
                  {productTypes.map((item) => (
                    <option key={item.productTypeId} value={item.productTypeId}>
                      {item.productTypeName}
                    </option>
                  ))}
                </select>
              </label>
            </div>

            <div className="col-lg-2 col-6 row">
              <label>
                Sắp xếp
                <select
                  name="sortType"
                  id="sortType"
                  value={searchingParams.sortType}
                  onChange={handleInputChange}
                  className="form-control "
                >
                  {options.map((item) => (
                    <option key={item.key} value={item.value}>
                      {item.text}
                    </option>
                  ))}
                </select>
              </label>
            </div>
            <div className="d-flex align-items-center col-lg-1 col-6">
              <button className="btn btn btn-primary w-50">
                <i className="fa-solid fa-magnifying-glass"></i>
              </button>
            </div>
          </form>
        </div>
        <div className="row">
          <table className="table table-striped table-hover border table-responsive">
            <thead className="thead-dark">
              <tr>
                <th colSpan="6">
                  <select
                    className="pageSize"
                    onChange={(e) => {
                      setPageSize(e.target.value);
                      setPage(1);
                    }}
                  >
                    <option value="10">10</option>
                    <option value="15">15</option>
                    <option value="20">20</option>
                  </select>
                  <button
                    className={`btn btn-sm m-1
             ${activeFlg === 5 ? "btn-success" : "btn-dark"}
           `}
                    onClick={() => {
                      handleactiveFlgChange(5);
                    }}
                  >
                    TẤT CẢ
                  </button>
                  <button
                    className={`btn btn-sm m-1
             ${activeFlg === 1 ? "btn-success" : "btn-dark"}
           `}
                    onClick={() => {
                      handleactiveFlgChange(1);
                    }}
                  >
                    ĐANG BÁN
                  </button>
                  <button
                    className={`btn btn-sm m-1
                    ${activeFlg === 0 ? "btn-success" : "btn-dark"}
                  `}
                    onClick={() => {
                      handleactiveFlgChange(0);
                    }}
                  >
                    TẠM NGƯNG
                  </button>
                </th>
                <th colSpan="4">
                  <h6>
                    <strong>CÓ {totalElements} SẢN PHẨM ĐƯỢC TÌM THẤY</strong>
                  </h6>
                </th>

                <th colSpan="2" className="text-end">
                  <NavLink to="/add-new">
                    <button className="btn btn btn-primary m-1">
                      THÊM MỚI
                    </button>
                  </NavLink>
                </th>
              </tr>
            </thead>
            <thead className="thead-dark">
              <tr className="text-center table-dark">
                <th>No.</th>
                <th>MÃ SP</th>
                <th>TÊN SẢN PHẨM</th>
                <th>ĐƠN VỊ </th>
                <th>GIÁ NHẬP</th>
                <th>GIÁ BÁN LẺ</th>
                {/* <th>CHIẾT KHẤU (%)</th> */}
                <th>GIÁ KHUYẾN MÃI</th>
                <th>NHẬP KHO</th>
                <th>ĐÃ BÁN</th>
                <th>TỒN KHO</th>
                <th>THAO TÁC</th>
              </tr>
            </thead>
            <tbody>
              {products.map((productItem, index) => {
                return (
                  <tr
                    disabled={productItem.activeFlg === 1}
                    className="text-center"
                    key={index}
                  >
                    <td>
                      <strong>{(page - 1) * pageSize + index + 1}</strong>
                    </td>
                    <td>{productItem.productCode}</td>
                    <td className="text-start">{productItem.productName}</td>
                    <td>{productItem.unit}</td>
                    <td>{productItem.costPrice}</td>
                    <td>{productItem.retailPrice}</td>
                    {/* <td>{productItem.discountPercent}</td> */}
                    <td>{productItem.salePrice}</td>
                    <td>{productItem.quantity}</td>
                    <td>{productItem.soldQuantity}</td>
                    <td>{productItem.remainingQuantity}</td>
                    <td>
                      <div className="d-flex justify-content-around">
                        <Link
                          as={Link}
                          to={`/edit/${productItem.id}`}
                          className="text-white"
                        >
                          <i className="fa-regular fa-pen-to-square text-primary"></i>
                        </Link>
                        {productItem.activeFlg === 1 ? (
                          <i
                            title={
                              productItem.activeFlg === 1
                                ? "TẠM NGỪNG KINH DOANH"
                                : ""
                            }
                            className="fa-solid fa-triangle-exclamation text-danger m-1"
                          ></i>
                        ) : (
                          <Link onClick={() => deleteItem(productItem.id)}>
                            <i className="fa-solid fa-trash text-secondary"></i>
                          </Link>
                        )}
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>

      <div className="center">
        {totalElements > pageSize && (
          <Pagination
            color="primary"
            className="my-3"
            count={totalPages}
            page={page}
            siblingCount={1}
            boundaryCount={1}
            variant="outlined"
            onChange={handlePageChange}
          />
        )}
      </div>
    </div>
  );
};

export default ProductListAdmin;
