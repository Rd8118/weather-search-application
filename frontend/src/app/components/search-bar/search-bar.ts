import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-bar.html',
  styleUrls: ['./search-bar.css']
})
export class SearchBar {
  @Output() searchCity = new EventEmitter<string>();
  
  cityName: string = '';
  errorMessage: string = '';

  /**
   * Handles search button click
   */
  onSearch(): void {
    this.errorMessage = '';
    
    // Validate input
    if (!this.cityName || this.cityName.trim().length === 0) {
      this.errorMessage = 'Please enter a city name';
      return;
    }

    if (this.cityName.trim().length < 2) {
      this.errorMessage = 'City name must be at least 2 characters';
      return;
    }

    // Emit search event
    this.searchCity.emit(this.cityName.trim());
  }

  /**
   * Handles Enter key press in input field
   */
  onKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.onSearch();
    }
  }

  /**
   * Clears input and error message
   */
  clearSearch(): void {
    this.cityName = '';
    this.errorMessage = '';
  }
}