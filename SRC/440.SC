;**
;**	Logics for room 440 -- Backstage with Cherri
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				440)

(public
	rm440				0
)

(procedure
	PrintCherri
	PrintLarry
)


(define	vBackstageClothes			440)
	(define	lCclothes			0)
		(define	cHat			0)
		(define	cTail			1)
		(define	cBra			2)
		(define	cLclothes	3)

(define	vLbackstageZiegfield		441)
;	(define	lRegularWalkers	0-3)

(define	vBackstageUndressing		442)
	(define	lCwalkingLeft		0)
	(define	lCremovingHat		1)
	(define	lCremovingTail		2)
	(define	lCremovingBra		3)
	(define	lLremovingSuit		4)
	(define	lCwalkingRight		5)
	(define	lLwalkingLeft		6)
	(define	lCreachingForL		7)

(define	vL&CdoingIt					443)
	(define	lGoingDown			0)
	(define	lMild					1)
	(define	lWarm					2)
	(define	lHot					3)
	(define	lHottest				4)

(define	vLbackstage					444)

(define	vLremovingZiegfield		445)
	(define	lLremovingHat		0)
	(define	lLremovingTail		1)
	(define	lLremovingBra		2)
	(define	lLwearingSuit		3)

(define	vCbackstageZiegfield		446)


;(define	sForeplay					008)	
;(define	sInterrupted				009)	


(define	inShadows			(& (ego onControl origin) cLtBlue))
(define	nearClothes			(ego inRect 121 127 199 159))


(define	humpX					114)
(define	humpY					151)
(define	CherriX				 94)
(define	LarryX				155)


(enum												;**	RoomScript state values
	intro
	startDoingIt
	CgoesBlack
	someoneIsInPlace
	bothInPlace
	hereWeAre
	takeOffHat
	hatsOff
	removeTail
	tailsOff
	removeBra
	braless
	gotoLarry
	atLarry
	undressLarry
	nakedLarry
	moveToCherri
	LatCherri
	theyAreDown
	realEstate
	mild
	warm
	hot
	hottest
	doneHumping
	youreTheOnlyOne
	drumRoll
	ohNo
	ohNo2
	scramble
	catchUs
	announcement
	changeRooms
	wearSuit
	removeLhat
	removeLtail
	removeLbra
	putOnSuit
	grabHisClothes
	inSuit
	allDone
)


(local
	humpSeconds
	seenMsg
)


(instance rm440 of Room
	(properties
		picture		440
		east			420
	)

	(method (init)
		(StatusLine	disable)
		(TheMenuBar	hide)

		(Load VIEW vBackstageClothes)
		(Load VIEW vLbackstage)

		(super init)

		(self
			setScript 		RoomScript,
		)

		(NormalEgo facingLeft)
		(ego
			viewer			shadowViewer,
			posn				315 humpY,
			observeBlocks	theCage,
			init,
		)

		
		(cond
			((not (TstFlg beenIn440))		;**	This is it! Let's do it.
				(SetFlg beenIn440)
				(SetFlg noCursor)
				(Load VIEW vL&CdoingIt)
				(Load VIEW vBackstageUndressing)
				(Load SOUND sForeplay)
				(Load SOUND sTimpaniRoll)
				(Load PICTURE blackPicture)
				(HandsOff)
				(aCherri
					init,
					setCycle			Walk,
					ignoreActors,
				)
				(RoomScript changeState startDoingIt)
			)
			((== egoState WEARING_ZIEGFIELD) ;**	He came in only for his clothes
				(Load VIEW vLbackstageZiegfield)
				(Load VIEW vLremovingZiegfield)
				(aLclothes
					ignoreActors,
					init
				)
			)
;**				(else
;**					(Print "ERROR! How'dju get in here?")
;**				)
		)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'smell')
				(Print "Suddenly, you feel hungry for tuna.")
			)

			((or	(Said 'undress') 
					(Said '(change<(out<of), from), (off<take), (take<off), remove/
						outfit, dress, clothes')
					(Said 'dressed<get')
					(Said 'get/dressed')
					(Said 'wear, get, (change<into, to), (put<on)//clothes, suit[<leisure]')
					(Said 'wear, get, (change<into, to), (put<on)/clothes, suit[<leisure]'))
				(cond
					((not nearClothes)
						(NotClose)
					)
					((!= egoState WEARING_ZIEGFIELD)
						(Print "You have no desire to wear women's clothing again.")
						(Print "Well, perhaps a little desire.")
						(Print "But you'll do your best to sublimate it!")
					)
					(else
						(self changeState wearSuit)
					)
				)
			)

			((Said 'look>')
				(cond
					((Said '/light, ceiling')
						(Print "The lights are once again dimmed.
							You vividly remember how they felt when you
							were on-stage dancing.")
					)

					((and	(== egoState WEARING_ZIEGFIELD)
							(Said '/stage, floor')
							(Print "There they are! Your clothes are still here,
								right where you left them.")
						)
					)

					((Said '/clothes, hat, bra, pants')
						(if (== egoState WEARING_ZIEGFIELD)
							(Print "There they are! Your clothes are still here,
								right where you left them.")
						else
							(Print "You are so happy to once again be back in your
								white leisure suit.")
							(Print "(Although you are becoming more and more
								fond of cross-dressing!)" LOW)
						)
					)

					((Said '/prop, set')
						(Print "Say! What the heck are they doing there?")
					)

					((Said '/bottle')
						(Print "The set pieces here are very strange.")
					)

					((Said '[/room, backstage]')
						(Print "Backstage is an interesting sight.
							Those set pieces always look so real from out front,
							but from here you can tell they are merely a facade.")
						(Print "(Much like your life, Larry!)" LOW)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 white)

		(switch (= state newState)

			(intro
			)

			;**	This time Larry's gonna get Cherri's cherry!

			(startDoingIt
				(ego
					setMotion		MoveTo LarryX humpY self,
				)
				(aCherri
					setMotion		MoveTo 210 humpY self,
				)
			)

			(CgoesBlack
				(PrintCherri "\"Larry! You're so wonderful. 
					You've given me what I've always wanted.\"")
				(aCherri
					view				vBackstageUndressing,
					setLoop			lCwalkingLeft,
					setMotion		MoveTo CherriX humpY self,
				)
			)

			(someoneIsInPlace					;**	whichever one gets there first
				(PrintLarry "\"I think you know what I've always wanted!\"")
			)

			(bothInPlace
			   (aCherri
					setLoop			lCremovingHat,
					cel				0,
				)
				(delay 3)
			)

			(hereWeAre
				(PrintCherri "Cherri smiles at you in the darkness,
					\"At last I'll be able to remove this costume
					never to wear it again!\"")
				(music
					number			sForeplay,
					loop				FOREVER,
					play
				)
				(delay 3)
			)

			(takeOffHat
			   (aCherri
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(hatsOff
			   (aCherri
					setLoop			lCremovingTail,
					cel				0,
				)
				(aHat
					ignoreActors,
					init,
					stopUpd,
				)
				(delay 3)
			)

			(removeTail
			   (aCherri
					setCycle			EndLoop self,
				)
			)

			(tailsOff
			   (aCherri
					setLoop			lCremovingBra,
					cel				0,
				)
				(aTail
					ignoreActors,
					init,
					stopUpd,
				)
				(delay 3)
			)

			(removeBra
			   (aCherri
					setCycle			EndLoop self,
				)
			)

			(braless
				(aBra
					ignoreActors,
					init,
					stopUpd,
				)
				(delay 3)
			)

			(gotoLarry
				(aCherri
					setLoop			lCwalkingRight,
					setCycle			Walk,
					setMotion		MoveTo humpX humpY self,
				)
			)

			(atLarry
				(PrintCherri "\"What are you? Modest?
					Hurry up, Larry, the second show's about to start.\"")
				(delay 3)
			)

			(undressLarry
				(Print "You carefully consider her offer...")
				(Print "...but not for long!")
				(= egoState DOING_IT)
				(ego
					viewer			FALSE,
					view				vBackstageUndressing,
					loop				lLremovingSuit,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(nakedLarry
				(aLclothes
					ignoreActors,
					init,
					stopUpd,
				)
				(ego
					setLoop			lLwalkingLeft,
					cel				0,
					setCycle			Walk,
				)
				(delay 2)
			)

			(moveToCherri
				(aCherri
					loop				lCreachingForL,
					cel				0,
					setCycle			EndLoop ,
				)
				(ego
					setMotion		MoveTo 145 humpY self,
				)
			)

			(LatCherri
				(aCherri				dispose)
				(ego
					view				vL&CdoingIt,
					loop				lGoingDown,
					cel				0,
					posn				humpX humpY,
					setCycle			EndLoop self,
				)
			)

			(theyAreDown
				(delay 3)
			)

			(realEstate
				(PrintCherri "\"Oh, Larry!\" cries Cherri.
					\"Don't you just love the real estate business?!\"")
				(delay 3)
			)

			(mild
				(if (< filthLevel CLEAN)
					(self changeState doneHumping)
				else
					(ego
						viewer			humpCycler,
						loop				lMild,
						cel				0,
						setCycle			Forward,
					)
					(delay 3)
				)
			)

			(warm
				(if (<= filthLevel RISQUE)
					(self changeState doneHumping)
				else
					(ego
						loop			lWarm,
					)
					(delay 3)
				)
			)

			(hot
				(if (<= filthLevel DIRTY)
					(self changeState doneHumping)
				else
					(ego
						loop			lHot,
					)
				)
				(delay 3)
			)

			(hottest
				(if ( < filthLevel RAUNCHY)
					(self changeState doneHumping)
				else
					(ego
						loop			lHottest,
					)
				)
				(delay 3)
			)

			(doneHumping
				(PrintCherri "\"Oh, Larry!
					I could go on like this all night!\"")
				(delay 3)
			)

			(youreTheOnlyOne
				(PrintLarry "\"You're the only one!\"")
				(delay 3)
			)

			(drumRoll
				(music
					number			sTimpaniRoll,
					loop				FOREVER,
					play,
				)
				(curRoom drawPic blackPicture WIPELEFT)
				(cast eachElementDo #hide)
				(PrintLarry "\"Who turned out that light?\"")
				(= cycles 11)
			)

			(ohNo
				(PrintLarry "\"What's that drum roll!?\"")
				(delay 3)
			)

			(ohNo2
				(PrintCherri "\"Oh, no!!\" she cries,
					\"Quick, Larry! Get dressed!
					The second show is starting!!\"")
				(delay 3)
			)

			(scramble
				(Print "You both scramble to find, sort, and wear whatever
					articles of clothing you can!")
				(delay 3)
			)

			(catchUs
				(PrintLarry "\"Geez, it's dark, Cherri,\"
					you shout, \"I can't tell what's what!\"")
				(delay 3)
			)

			(announcement
				(Print "The public address system booms,
					\"Lady and gentlemen, welcome to `Nontoonyt For You,'
					our spectacular tribute to the thing we all love most.\""
					BIG)
				(delay 2)
			)

			(changeRooms
				(PrintLarry "\"Where's my pants?\"")
				(systemWindow
					color 		textColor,
					back			backgroundColor,
				)
				(curRoom newRoom 431)
			)


			;**	This is where Larry changes back to his own clothes.

			(wearSuit
				(Ok)
				(HandsOff)
				(ego
					illegalBits		0,
					setMotion		MoveTo LarryX humpY self,
				)
			)

			(removeLhat
				(ego
					viewer			FALSE,
					view				vLremovingZiegfield,
					loop				lLremovingHat,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(removeLtail
				(aHat
					posn				LarryX humpY,
					ignoreActors,
					init,
					stopUpd,
				)
			   (ego
					setLoop			lLremovingTail,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(removeLbra
				(aTail
					posn				LarryX humpY,
					ignoreActors,
					init,
					stopUpd,
				)
			   (ego
					setLoop			lLremovingBra,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(putOnSuit
				(aBra
					posn				LarryX humpY,
					ignoreActors,
					init,
					stopUpd,
				)
				(ego
					loop				lLwearingSuit,
					cel				0,
					setCycle			EndLoop self,
				)
				(= cycles 9)
			)

			(grabHisClothes
				(aLclothes			dispose)
			)

			(inSuit
				(= showroomState  SRdone)	;**	This is last step.
				(= newspaperState NSpHere)	;**	Patti is now in lounge.
				(= egoState BACKSTAGE)
				(= normalEgoView vLarry)
				(NormalEgo facingWest vLbackstage)
				(ego viewer shadowViewer)
				(theGame changeScore 25)
				(= cycles 22)
			)

			(allDone
				(Print "Ah! Now that feels like home.")
			)
		)
	)
)

(instance aCherri of Actor
	(properties
		view				vCbackstageZiegfield
;		loop				facingWest
		x					290
		y					humpY
		illegalBits		0
	)
)

(instance aHat of View
	(properties
		view				vBackstageClothes
;		loop				lClothes
		cel				cHat
		x					63
		y					144
	)
)
(instance aTail of View
	(properties
		view				vBackstageClothes
;		loop				lClothes
		cel				cTail
		x					93
		y					148
	)
)
(instance aBra of View
	(properties
		view				vBackstageClothes
;		loop				lClothes
		cel				cBra
		x					83
		y					143
	)
)
(instance aLclothes of View
	(properties
		view				vBackstageClothes
;		loop				lClothes
		cel				cLclothes
		x					150
		y					145
	)
)

(instance theCage of Cage
	(properties
		top				144
		left				-15
		bottom			212
		right				335
	)
)
(instance shadowViewer of Code
	(method (doit)
		(ego view
			(cond
				((not inShadows)							normalEgoView)
				((== egoState WEARING_ZIEGFIELD)		vLbackstageZiegfield)
				((!= egoState DOING_IT)					vLbackstage)
				(else											(Print "error"))
			)
		)
	)
)
(instance humpCycler of Code
	(method (doit)
		(if (not (Random 0 9))
			(ego cycleSpeed (Random 0 5))
		)
	)
)
(procedure (PrintCherri &tmp)
	(systemWindow
		color 		ltRed,
		back			ltBlack,
	)
	(Print &rest
		#at 			20 -1
		#title		{Cherri says}
		#width 		150
	)
	(systemWindow
		color 		black,
		back			ltBlack,
	)
)
(procedure (PrintLarry &tmp)
	(systemWindow
		color 		ltBlue,
		back			ltBlack,
	)
	(Print &rest
		#at 			180 -1
		#title		{You say}
		#width 		120
	)
	(systemWindow
		color 		black,
		back			ltBlack,
	)
)

