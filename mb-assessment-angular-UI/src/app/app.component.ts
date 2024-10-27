import { Component, OnInit, inject ,ViewChild,ElementRef} from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';

import { HttpConnection } from './service/httpConnection';
import { MatPaginatorModule } from '@angular/material/paginator';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, ReactiveFormsModule,MatPaginatorModule],
  providers: [HttpConnection],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'mb-assessment';
 
  connection = inject(HttpConnection);
  ngOnInit() {
    //
  }
 
  customerList: any[] = [];
  customer: any = {};
  customerUdp: any = {};

  totalPage: number = 2;
  currentPage: number = 0;
  totalItems: number =0;

  searchForm = new FormGroup({
    customerId: new FormControl(''),
    accountNumber: new FormControl(''),
	desc: new FormControl(''),
  });

  handleSearch() {
    console.log(
      'searchForm ',
      this.searchForm.value.customerId +
        ' | ' +
        this.searchForm.value.accountNumber
    );
  this.currentPage = 0;
    const resultList = this.getCustomers(this.searchForm.value.customerId, this.searchForm.value.accountNumber, this.searchForm.value.desc,this.currentPage);
    console.log('resultList ' + JSON.stringify(resultList));

 
  }
  
  closeModal(){
  	let myModal = document.getElementById('myModal');
	if (myModal) myModal.style.display = 'none';
  }
  
 

  getCustomers(customerId: any,accountNumber: any,desc: any, currentPage: any) {
    const reqUrl = 'http://localhost:8080/transaction/search?account=' + accountNumber +  '&desc='+desc+'&custId='+customerId +'&page='+currentPage;

    this.connection.getData(reqUrl).subscribe({
      next: (response) => {
        this.customerList = response.content;
		this.totalItems = response.totalElements
        console.log('this.customerList:', this.customerList);
		console.log('response:', response);
      },
      error: (error) => {
        console.error('Error fetching data:', error);
      },
    });
  }
  
  pageChanged(event: any) {
  this.currentPage = event.pageIndex;
   
 
   const resultList = this.getCustomers(this.searchForm.value.customerId, this.searchForm.value.accountNumber, this.searchForm.value.desc,this.currentPage);
 // this.items = this.getData(this.currentPage, this.pageSize);
}

  getCustomerDetail(customerId: any) {
  
    let reqUrl = 'http://localhost:8080/transaction/' + customerId;
	let myModal = document.getElementById('myModal');
	if (myModal) myModal.style.display = 'block';
	
	
	let formUpd = document.getElementById('updateForm');
	if (formUpd) formUpd.style.display = 'block';
	let form = document.getElementById('buttonUpd');
	if (form) form.focus();
 
    this.connection.getData(reqUrl).subscribe({
      next: (response) => {
        this.customer = response;

        console.log('this.customer:', this.customer);
      },
      error: (error) => {
        console.error('Error fetching data:', error);
      },
    });
  }

  viewDetail(customer: any) {
 

    this.getCustomerDetail(customer);
 
  }
  
  //-----------------
  
  openModal(viewUserTemplate: any) {
     
  }
  
 
  //------------------

  updateForm = new FormGroup({
    id: new FormControl(this.customer.id),
    description: new FormControl(this.customer.description)
  });

  handleUpdate() {
    console.log(
      'updateForm1 ',
      this.updateForm.value.id +
        ' | ' +
        this.updateForm.value.description 

    );

     

    this.updateCustomers(this.updateForm.value.description);
  }

  updateCustomers(newDesc: any) {
    const reqUrl = 'http://localhost:8080/transaction/updateDesc';
 
this.customerUdp.id = this.customer.id;
this.customerUdp.description = newDesc;
this.customerUdp.oldDescription = this.customer.description;
console.log(
      'update data ',
      this.customerUdp.description +
        ' | ' +
        this.customerUdp.oldDescription 

    );
    this.connection.putData(reqUrl, this.customerUdp).subscribe({
      next: (response) => {
        alert('Updated successfully !');
 this.customer.description = newDesc;
		 const resultList = this.getCustomers(this.searchForm.value.customerId, this.searchForm.value.accountNumber, this.searchForm.value.desc,this.currentPage);
      },
      error: (error) => {
	   alert('Oops ! Something goes wrong');
        console.error('Error fetching data:', error);
      },
    });
  }
}

 
