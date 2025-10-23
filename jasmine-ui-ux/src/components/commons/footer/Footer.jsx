import React, { useState } from "react";

import "./footer.css";

function Footer() {
  return (
    <div className="container">
      <footer className="footer">
        <div className="footer_row">
          <div className="footer_col">
            <h4 className="footer_col_heading">KHÁCH HÀNG</h4>
            <ul className="footer_col_list_parent">
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Trung tâm trợ giúp
                </a>
              </li>
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Mẹo vặt cuộc sống
                </a>
              </li>
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Trả hàng và hoàn tiền
                </a>
              </li>
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Gửi Yêu Cầu Hỗ Trợ
                </a>
              </li>
            </ul>
          </div>

          <div className="footer_col">
            <h4 className="footer_col_heading">VỀ SILVA FARM</h4>
            <ul className="footer_col_list_parent">
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Về chúng tôi
                </a>
              </li>
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Quy chế hoạt động
                </a>
              </li>
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Chính Sách Bảo Mật
                </a>
              </li>

              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Giải Quyết Khiếu Nại
                </a>
              </li>
            </ul>
          </div>

          <div className="footer_col">
            <h4 className="footer_col_heading">THANH TOÁN</h4>
            <ul className="footer_col_list_parent">
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Customer Agreement
                </a>
              </li>
            </ul>
            <h4 className="footer_col_heading">VẬN CHUYỂN</h4>
            <ul className="footer_col_list_parent">
              <li className="footer_col_lists">
                <a className="footer_col_links" href="#">
                  Customer Agreement
                </a>
              </li>
            </ul>
          </div>

          <div className="footer_col">
            <h4 className="footer_col_heading">KẾT NỐI SILVA FARM</h4>
            <div className="icons">
              <i className="fa-brands fa-facebook-f"></i>
              <i className="fa-brands fa-twitter"></i>
              <i className="fa-brands fa-linkedin"></i>
              <i className="fa-brands fa-github"></i>
            </div>
            <h4 className="footer_col_heading">NHẬN THÔNG TIN ƯU ĐÃI</h4>
            <form action="#">
              <input type="text" placeholder="Your email" required />
              <button type="submit">SUBSCRIBE</button>
            </form>
          </div>
        </div>
      </footer>
    </div>
  );
}

export default Footer;
