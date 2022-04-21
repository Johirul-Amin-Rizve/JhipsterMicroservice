import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'department',
        data: { pageTitle: 'frontEnd1App.department.home.title' },
        loadChildren: () => import('./department/department.module').then(m => m.DepartmentModule),
      },
      {
        path: 'teacher',
        data: { pageTitle: 'frontEnd1App.teacher.home.title' },
        loadChildren: () => import('./teacher/teacher.module').then(m => m.TeacherModule),
      },
      {
        path: 'student',
        data: { pageTitle: 'frontEnd1App.student.home.title' },
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
