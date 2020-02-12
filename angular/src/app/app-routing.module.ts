import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { RateMovieComponent } from './rate-movie/rate-movie.component';
import { RatedMoviesComponent } from './rated-movies/rated-movies.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', redirectTo: localStorage.getItem('token') == null  
  || sessionStorage.getItem('token') == null ? 'sign-in' : 'rate-movie', 
  pathMatch: 'full'},
  {path: 'sign-in', component: SignInComponent},
  {path: 'rate-movie', component: RateMovieComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'rated-movies', component: RatedMoviesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
