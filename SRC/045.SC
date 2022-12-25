;**
;**	Logics for script 45 -- Locker combination script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 21, 1989
;**


(script#			LOCKER)

(public
	LockerScript	0
)


(local
	[name1	20] 
	[name2	20] 
	[name3	20] 
)


(instance LockerScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(0
				(HandsOff)
				(if (TstFlg scoredCombination)
					(self cue)
				else
					(theGame changeScore 65)
					(Print "Well, well.
						Look at this!
						Suzi wrote her locker number plus some words on the back
						of her spa keycard."
				 		#icon iKeycard 0 1	;**	cel 1 is the back of the card
					)
					(delay 2)
				)
			)

			(1
				(if (TstFlg scoredCombination)
					(Format @name1 "%s" 046 lockerCombination1)
					(Format @name2 "%s" 046 lockerCombination2)
					(Format @name3 "%s" 046 lockerCombination3)
					(Printf "Don't you ever take notes? It says,
						\"%s,\" \"%s,\" and \"%s!\""
						@name1 @name2 @name3)
				else
					(SetFlg scoredCombination)

					(Format @name1 "%s" 046 (= lockerCombination1 0))
					(while (== SPACEBAR (StrAt @name1 0))
						(= lockerCombination1 (Random 1 LAST_DOCO_PAGE))
						(Format @name1 "%s" 046 lockerCombination1))

					(= lockerCombination2 lockerCombination1)
					(while (or	(== lockerCombination1 lockerCombination2)
									(== SPACEBAR  (StrAt @name2 0)))
						(= lockerCombination2 (Random 1 LAST_DOCO_PAGE))
						(Format @name2 "%s" 046 lockerCombination2))

					(= lockerCombination3 lockerCombination1)
					(while (or	(== lockerCombination1 lockerCombination3)
									(== lockerCombination2 lockerCombination3)
									(== SPACEBAR  (StrAt @name3 0)))
						(= lockerCombination3 (Random 1 LAST_DOCO_PAGE))
						(Format @name3 "%s" 046 lockerCombination3))
					(Printf "Closer examination reveals the words to be: 
						\"%s,\" \"%s,\" and \"%s.\" Now, what could that mean?
						Why would Suzi scribble the names of Nontoonyt businesses 
						on the back of her spa membership keycard?"
						@name1 @name2 @name3)
				)

				(HandsOn)
				(theGame setScript FALSE)
				(DisposeScript LOCKER)
				(DisposeScript LOCKER_TEXT)
			)
		)
	)
)

;**	Use this to test such prints:
;**	
;**								(Printf "name1 is \"%s\".
;**									Value of 1st char of name1 = %d."
;**									@name1 
;**									(StrAt @name1 0))

