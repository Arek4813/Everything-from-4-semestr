with Ada.Text_IO; use Ada.Text_IO;
with Ada.Command_Line; use Ada.Command_Line;
with Ada.Numerics.Float_Random; use Ada.Numerics.Float_Random;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Float_Text_IO; use Ada.Float_Text_IO;
with Ada.Containers.Doubly_Linked_Lists; use Ada.Containers;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;

procedure Main is

   --CONSTANTS
   workerAmount : Integer :=2;
   workerTimerSec : Integer := 3;
   clientTimerSec : Integer := 4;


   --BOOLEAN VARIABLE TO DECIDE IF TEXT SHOULD BE PRINTED
   isTalker: Boolean := false;

   --STRUCTURE OF REQUEST
   type request is record
      firstArg : float;
      secondArg : float;
      operationSign : Character;
   end record;


   -- REQUESTS AND PRODUCTS
begin
   declare
   package RExample is new Ada.Containers.Doubly_Linked_Lists(request);
   TasksList : RExample.List;
   package FExample is new Ada.Containers.Doubly_Linked_Lists(Float);
   ProductsList : FExample.List;




   --"CHANNELS"
   protected TCHAN is
      procedure Set (T: request);
      entry Get (T: out request);
   private
      Is_Set : Boolean := False;
   end TCHAN;

   protected body TCHAN is
      procedure Set (T: request) is
      begin
         TasksList.Append(T);
         Is_Set := True;
      end Set;

      entry Get (T: out request) when Is_Set is
      begin
         T := TasksList.First_Element;
         TasksList.Delete_First;
         Is_Set:=False;
      end Get;
   end TCHAN;

   protected PCHAN is
      procedure Set (T: float);
      entry Get (T: out float);
   private
      Is_Set : Boolean := False;
   end PCHAN;

   protected body PCHAN is
      procedure Set (T: float) is
      begin
            ProductsList.Append(T);
            Is_Set := True;
      end Set;

      entry Get (T: out float) when Is_Set is
      begin
            T := ProductsList.First_Element;
            ProductsList.Delete_First;
            Is_Set := False;
      end Get;
   end PCHAN;


   oneRequest : request;
   oneProduct : float;



   --TASKS
   task type Boss is
      entry CreateReq;
   end Boss;

   task body Boss is
      G : Generator;
      Rand1 : Float;
      Rand2 : Float;
      SignRand : Float;
   begin
      loop
         select
            accept CreateReq;
               loop
               Reset(G);
               Rand1 := Random(G);
               Rand2 := Random(G);
               SignRand := Random(G);
               oneRequest.firstArg := Rand1;
               oneRequest.secondArg := Rand2;
               if(SignRand<0.3333) then
                  oneRequest.operationSign := '+';
               elsif(SignRand<0.6666) then
                  oneRequest.operationSign := '*';
               else
                  oneRequest.operationSign := '-';
               end if;
               TCHAN.Set(oneRequest);
               if(isTalker=true) then
                  Put_Line("Boss created request");
                  Put_Line("This request: " & Float'Image(Rand1) & " " & Character'Image(oneRequest.operationSign) & "" & Float'Image(Rand2));
                  New_Line;
               end if;
            delay 0.1+Duration(2.0*Random(G));
            end loop;
         end select;
      end loop;
   end Boss;


   task type Worker is
      entry CreateProd;
   end Worker;

   task body Worker is
   begin
      loop
         select
            accept CreateProd;
               loop
               TCHAN.Get(oneRequest);
               case oneRequest.operationSign is
               when '+' =>
                  oneProduct := oneRequest.firstArg + oneRequest.secondArg;
               when '-' =>
                  oneProduct := oneRequest.firstArg - oneRequest.secondArg;
               when '*' =>
                  oneProduct := oneRequest.firstArg * oneRequest.secondArg;
               when others =>
                  Put_Line("Bad sign");
               end case;
               PCHAN.Set(oneProduct);
               if(isTalker=true) then
                  Put_Line("Worker done a product");
                  Put_Line("Made product: " & Float'Image(oneProduct));
                  New_Line;
               end if;
               delay Duration(workerTimerSec);
               end loop;
         end select;
      end loop;
   end Worker;


   task type Customer is
       entry BuyProduct;
   end Customer;


   task body Customer is
   begin
      loop
         select
            accept BuyProduct;
               loop
               PCHAN.Get(oneProduct);
               if(isTalker=true) then
                  Put_Line("Customer bought a product");
                  New_Line;
               end if;
               delay Duration(clientTimerSec);
               end loop;
         end select;
      end loop;
   end Customer;


      type workCrew is array (1 .. workerAmount) of Worker;


      elBossos : Boss;
      elWorkeros : workCrew;
      elCustomeros : Customer;

      decider : Integer;
      wCounter : Integer := 0;
      cCounter : Integer := 0;



      --CURSORS TO SHOW DOUBLY_LINKED_LIST ELEMENTS -> PRODUCTS AND TASKS
      procedure FPrint (Position : in FExample.Cursor) is
   begin
         Ada.Float_Text_IO.Put (FExample.Element (Position));
         Put_Line("");
   end FPrint;


      procedure RPrint (Position : in RExample.Cursor) is
   begin
         Ada.Float_Text_IO.Put (RExample.Element (Position).firstArg);
         Put(" and");
         Ada.Float_Text_IO.Put (RExample.Element (Position).secondArg);
         Put(" with ");
         Ada.Text_IO.Put (RExample.Element (Position).operationSign);
         Put_Line("");
   end RPrint;

begin


   if Argument(1)="talker" then
      isTalker := true;
      Put_Line("You choosed talker mode.");
      New_Line;
      elBossos.CreateReq;
      --while wCounter<workerAmount loop
         --instruction
         --wCounter:=wCounter+1;
      --end loop;
      for I in elWorkeros'Range loop
         elWorkeros(I).CreateProd;
      end loop;
      elCustomeros.BuyProduct;

   elsif Argument(1)="silent" then
      isTalker := false;
      Put_Line("You choosed silent mode.");
      New_Line;
      elBossos.CreateReq;
      for I in elWorkeros'Range loop
         elWorkeros(I).CreateProd;
      end loop;
      elCustomeros.BuyProduct;
      loop
         Put_Line("-----THE WHOLE SIMULATION WORK IN BACKGROUND-----");
         Put_Line("----------> 1) Show me state of productContainer");
         Put_Line("----------> 2) Show me the list of tasks");
         New_Line;
         Get(decider);
         Put_Line("");
         case decider is
            when 1 =>
               ProductsList.Iterate(FPrint'Access);
            when 2 =>
               TasksList.Iterate(RPrint'Access);
            when others =>
               Put_Line("You clicked wrong number! Choose option 1 or 2!");
         end case;
         New_Line;
      end loop;
   else
      Put_Line("You choosed wrong mode. Try talker or silent mode.");
      New_Line;
      end if;
   end;
end Main;



