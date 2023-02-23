with Ada.Text_IO;
use Ada.Text_IO;

procedure Main is

   can_stop : boolean := false;
   thred_number : Integer := 0;
   pragma Atomic(can_stop);

   task type break_thread;
   task type main_thread;

   task body break_thread is
   begin
      delay 10.0;
      can_stop := true;
   end break_thread;

   task body main_thread is
      sum : Long_Long_Integer := 0;
      step : Long_Long_Integer := 50;
      i : Long_Long_Integer := 0;
      flag : Integer := 0;
   begin
      loop
         sum := sum + step;
         i := i + 1;
         exit when can_stop;
      end loop;
      thred_number := thred_number + 1;
      Put_Line(thred_number'Img);

      Put_Line(sum'Img);
      Put_Line(i'Img);
   end main_thread;

   b1 : break_thread;
   t1 : main_thread;
   t2 : main_thread;
   t3 : main_thread;
   t4 : main_thread;
   t5 : main_thread;
   t6 : main_thread;
begin
   null;
end Main;
