import {Directive, ElementRef, HostBinding, HostListener, Renderer2} from '@angular/core';

@Directive({
  selector: '[appDropdown]'
})
export class DropdownDirective {
  @HostBinding('class.show') isShow = false;

  @HostListener('document:click', ['$event']) toggleOpen(event: Event) {
    console.log(event);
    console.log(this.elRef.nativeElement.contains(event.target));
    console.log(this.elRef.nativeElement);
    this.isShow = this.elRef.nativeElement.contains(event.target) ? !this.isShow : false;
  }

  constructor(private elRef: ElementRef) {
  }
}
